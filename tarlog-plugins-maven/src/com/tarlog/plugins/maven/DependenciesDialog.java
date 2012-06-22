package com.tarlog.plugins.maven;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

public class DependenciesDialog extends TitleAreaDialog {

	private static final Pattern DOT_PATTERN = Pattern.compile("\\.");
	private CheckboxTableViewer dependenciesTableViewer;
	private TableViewer newPropertiesTableViewer;
	private final Properties existingProperties;
	private final Properties newProperties = new Properties();

	private final List<RowDependency> tableModel;

	public DependenciesDialog(Shell parentShell, Model model) {
		super(parentShell);

		existingProperties = model.getProperties();
		tableModel = createTableModel(model);

	}

	@Override
	protected Control createContents(Composite parent) {
		Control contents = super.createContents(parent);
		setTitle("Dependencies");
		setMessage("List of depenencies to be replaced with properties",
				IMessageProvider.INFORMATION);
		return contents;
	}

	private List<RowDependency> createTableModel(Model model) {
		List<RowDependency> list = new ArrayList<DependenciesDialog.RowDependency>();
		Map<String, Map<String, Dependency>> toreplace = new HashMap<String, Map<String, Dependency>>();
		findDependenciesToReplace(toreplace, model.getDependencies());
		findDependenciesToReplace(toreplace, model.getDependencyManagement()
				.getDependencies());
		for (Entry<String, Map<String, Dependency>> entry : toreplace
				.entrySet()) {
			// if all artifacts in the same group have a single version,
			// propose a single replacement
			final Map<String, Dependency> group = entry.getValue();
			String prevVersion = null;
			int groupIndex = list.size();
			for (Iterator<Dependency> iterator = group.values().iterator(); iterator
					.hasNext();) {
				Dependency d = iterator.next();
				final String version = d.getVersion();
				if (prevVersion == null) {
					prevVersion = version;
					// single row, or first row
					if (group.size() > 1) {
						// first row, potentially same version for the whole
						// group
						list.add(new RowDependency(d, VersioningType.GROUP));
					} else {
						// single row
						list.add(new RowDependency(d, VersioningType.ARTIFACT));
					}
				} else if (prevVersion.equals(version)) {
					// same version, in the group
					list.add(new RowDependency(d, list.get(list.size() - 1)
							.getVersionProperty()));
				} else {
					// not same version
					// need to change the whole group!
					for (int i = groupIndex; i < list.size(); ++i) {
						list.get(i).uniqueVersion(VersioningType.ARTIFACT);
					}
					while (iterator.hasNext()) {
						list.add(new RowDependency(iterator.next(),
								VersioningType.ARTIFACT));
					}
				}
			}
		}
		return list;
	}

	private Map<String, Map<String, Dependency>> findDependenciesToReplace(
			Map<String, Map<String, Dependency>> toreplace,
			final List<Dependency> dependencies) {
		for (Dependency dependency : dependencies) {
			final String version = dependency.getVersion();
			if (!version.startsWith("${")) {
				final String groupId = dependency.getGroupId();
				Map<String, Dependency> group = toreplace.get(groupId);
				if (group == null) {
					group = new HashMap<String, Dependency>();
					toreplace.put(groupId, group);
				}
				final String artifactId = dependency.getArtifactId();
				group.put(artifactId, dependency);
			}
		}
		return toreplace;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		Label dependenciesLabel = new Label(composite, SWT.LEFT
				| SWT.HORIZONTAL);
		dependenciesLabel.setText("Dependencies:");
		createDependenciesTable(composite);
		Label newPropertiesLabel = new Label(composite, SWT.LEFT
				| SWT.HORIZONTAL);
		newPropertiesLabel.setText("New Properties:");
		createNewPropertiesTable(composite);
		return composite;
	}

	private void createDependenciesTable(Composite composite) {
		dependenciesTableViewer = CheckboxTableViewer.newCheckList(composite,
				SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION
						| SWT.BORDER);

		final Table table = dependenciesTableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		createGroupColumn(dependenciesTableViewer);
		createArtifactColumn(dependenciesTableViewer);
		createVersionPropertyColumn();

		dependenciesTableViewer.setContentProvider(ArrayContentProvider
				.getInstance());
		dependenciesTableViewer.setInput(tableModel);
		dependenciesTableViewer.setAllChecked(true);
		dependenciesTableViewer.setComparator(new ViewerComparator() {
			@Override
			public int compare(Viewer viewer, Object e1, Object e2) {
				RowDependency rd1 = (RowDependency) e1;
				RowDependency rd2 = (RowDependency) e2;
				return rd1.dependency.getGroupId().compareToIgnoreCase(
						rd2.dependency.getGroupId());
			}
		});
	}

	private void createNewPropertiesTable(Composite composite) {
		newPropertiesTableViewer = new TableViewer(composite, SWT.MULTI
				| SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);

		final Table table = newPropertiesTableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		createPropertyName(newPropertiesTableViewer);
		createPropertyValue(newPropertiesTableViewer);

		newPropertiesTableViewer
				.setContentProvider(PropertiesConentProvider.instance);
		newPropertiesTableViewer.setInput(newProperties);
		newPropertiesTableViewer.setComparator(new ViewerComparator() {
			@SuppressWarnings("unchecked")
			@Override
			public int compare(Viewer viewer, Object e1, Object e2) {
				Entry<String, String> entry1 = (Entry<String, String>) e1;
				Entry<String, String> entry2 = (Entry<String, String>) e2;
				return entry1.getKey().compareTo(entry2.getKey());
			}
		});
	}

	private void createPropertyName(final TableViewer tableViewer) {
		TableViewerColumn col = new TableViewerColumn(tableViewer, SWT.NONE);
		col.getColumn().setWidth(200);
		col.getColumn().setText("Name");
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				@SuppressWarnings("unchecked")
				Entry<String, String> entry = (Entry<String, String>) element;
				return entry.getKey();
			}

		});

	}

	private void createPropertyValue(final TableViewer tableViewer) {
		TableViewerColumn col = new TableViewerColumn(tableViewer, SWT.NONE);
		col.getColumn().setWidth(200);
		col.getColumn().setText("Value");
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				@SuppressWarnings("unchecked")
				Entry<String, String> entry = (Entry<String, String>) element;
				return entry.getValue();
			}
		});

	}

	private void createGroupColumn(final TableViewer tableViewer) {
		TableViewerColumn col = new TableViewerColumn(tableViewer, SWT.NONE);
		col.getColumn().setWidth(200);
		col.getColumn().setText("Group");
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				RowDependency d = (RowDependency) element;
				return d.dependency.getGroupId();
			}
		});

	}

	private void createArtifactColumn(final TableViewer tableViewer) {
		TableViewerColumn col = new TableViewerColumn(tableViewer, SWT.NONE);
		col.getColumn().setWidth(200);
		col.getColumn().setText("Artifact");
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				RowDependency d = (RowDependency) element;
				return d.dependency.getArtifactId();
			}
		});
	}

	private void createVersionPropertyColumn() {
		TableViewerColumn col = new TableViewerColumn(dependenciesTableViewer,
				SWT.NONE);
		col.getColumn().setWidth(200);
		col.getColumn().setText("Property");
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				RowDependency d = (RowDependency) element;
				return d.versionProperty;
			}
		});

		col.setEditingSupport(new EditingSupport(dependenciesTableViewer) {

			@Override
			protected void setValue(Object element, Object value) {
				RowDependency d = (RowDependency) element;
				String oldProperty = d.getVersionProperty();
				String newProperty = (String) value;
				String version = (String) newProperties.remove(oldProperty);
				newProperties.setProperty(newProperty, version);
				d.setVersionProperty(newProperty);
				dependenciesTableViewer.refresh();
				newPropertiesTableViewer.refresh();
			}

			@Override
			protected Object getValue(Object element) {
				RowDependency d = (RowDependency) element;
				return d.versionProperty;
			}

			@Override
			protected CellEditor getCellEditor(Object element) {
				return new TextCellEditor(dependenciesTableViewer.getTable());
			}

			@Override
			protected boolean canEdit(Object element) {
				return true;
			}
		});
	}

	private final class RowDependency {
		private final Dependency dependency;
		private String versionProperty;

		public RowDependency(Dependency dependency,
				VersioningType versioningType) {
			this.dependency = dependency;
			uniqueVersion(versioningType);
		}

		public void uniqueVersion(VersioningType versioningType) {
			String baseVersion = versioningType.getBaseVersion(dependency);
			if (tryToSetVersion(baseVersion)) {
				return;
			}

			// failed to build version using the versioning type, try full name
			baseVersion = getBriefGroupId(dependency.getGroupId()) + "."
					+ dependency.getArtifactId();
			if (tryToSetVersion(baseVersion)) {
				return;
			}

			baseVersion = dependency.getGroupId() + "."
					+ dependency.getArtifactId();
			if (tryToSetVersion(baseVersion)) {
				return;
			}

			// well, should not get here... just add some random sequence until
			// succeed
			while (true) {
				if (tryToSetVersion(baseVersion + UUID.randomUUID().toString())) {
					return;
				}
			}
		}

		private boolean tryToSetVersion(String baseVersion) {
			String versionCandidate = baseVersion + "-version";
			if (existingProperties.getProperty(versionCandidate) == null
					&& newProperties.getProperty(versionCandidate) == null) {
				newProperties.setProperty(versionCandidate,
						dependency.getVersion());
				versionProperty = versionCandidate;
				return true;
			}
			return false;
		}

		public RowDependency(Dependency dependency, String version) {
			super();
			this.dependency = dependency;
			this.versionProperty = version;
		}

		public String getVersionProperty() {
			return versionProperty;
		}

		public void setVersionProperty(String version) {
			this.versionProperty = version;
		}

	}

	private enum VersioningType {
		GROUP {

			@Override
			public String getBaseVersion(Dependency dependency) {
				return getBriefGroupId(dependency.getGroupId());
			}
		},
		ARTIFACT {

			@Override
			public String getBaseVersion(Dependency dependency) {
				return dependency.getArtifactId();
			}
		};

		public abstract String getBaseVersion(Dependency dependency);
	}

	private static String getBriefGroupId(String groupId) {
		String[] split = DOT_PATTERN.split(groupId);
		return split[split.length - 1];
	}

}
