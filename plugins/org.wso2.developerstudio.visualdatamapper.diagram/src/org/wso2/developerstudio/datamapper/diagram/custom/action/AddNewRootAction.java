/*
 * Copyright (c) 2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wso2.developerstudio.datamapper.diagram.custom.action;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.common.ui.action.AbstractActionHandler;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.impl.DiagramImpl;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.wso2.developerstudio.datamapper.DataMapperFactory;
import org.wso2.developerstudio.datamapper.DataMapperPackage;
import org.wso2.developerstudio.datamapper.DataMapperRoot;
import org.wso2.developerstudio.datamapper.PropertyKeyValuePair;
import org.wso2.developerstudio.datamapper.TreeNode;
import org.wso2.developerstudio.datamapper.diagram.Activator;
import org.wso2.developerstudio.datamapper.diagram.custom.util.AddNewObjectDialog;
import org.wso2.developerstudio.datamapper.diagram.edit.parts.DataMapperRootEditPart;
import org.wso2.developerstudio.datamapper.diagram.edit.parts.InputEditPart;
import org.wso2.developerstudio.datamapper.diagram.edit.parts.OutputEditPart;
import org.wso2.developerstudio.datamapper.impl.TreeNodeImpl;
import org.wso2.developerstudio.eclipse.logging.core.IDeveloperStudioLog;
import org.wso2.developerstudio.eclipse.logging.core.Logger;
import org.wso2.developerstudio.eclipse.registry.core.interfaces.IRegistryFile;

public class AddNewRootAction extends AbstractActionHandler {

	private EditPart selectedEP;
	private static final String OUTPUT_EDITPART = "Output"; //$NON-NLS-1$
	private static final String INPUT_EDITPART = "Input"; //$NON-NLS-1$
	private static final String ADD_NEW_ROOT_RECORD_ACTION_ID = "add-new-root-record-action-id"; //$NON-NLS-1$
	private static final String ADD_NEW_ROOT_RECORD = Messages.AddNewRootRecordAction_addNewRoot;
	private static final String ERROR_ADDING_MULTIPLE_ROOT_ELEMENTS = Messages.AddNewRootRecordAction_addMultipleRootElements;
	private static final String ERROR_ADDING_MULTIPLE_ROOT_ELEMENTS_TITLE = Messages.AddNewRootRecordAction_addMultipleRootElementsTitle;
	private static final String ERROR = Messages.AddNewRootRecordAction_errorHeader;
	private static final String ERROR_ADDING_NEW_CHILD = Messages.AddNewRootRecordAction_errorAddChild;
	private static final String DIALOG_TITLE = "Add new Root Element";

	private static final String JSON_SCHEMA_REQUIRED = "required";
	private static final String JSON_SCHEMA_SCHEMA_VALUE = "$schema";
	private static final String JSON_SCHEMA_ID = "id";
	private static final String JSON_SCHEMA_TYPE = "type";
	private static final String JSON_SCHEMA_NAMESPACES = "namespaces";

	private static IDeveloperStudioLog log = Logger.getLog(Activator.PLUGIN_ID);

	public AddNewRootAction(IWorkbenchPart workbenchPart) {
		super(workbenchPart);

		setId(ADD_NEW_ROOT_RECORD_ACTION_ID);
		setText(ADD_NEW_ROOT_RECORD);
		setToolTipText(ADD_NEW_ROOT_RECORD);
		ISharedImages workbenchImages = PlatformUI.getWorkbench().getSharedImages();
		setImageDescriptor(workbenchImages.getImageDescriptor(ISharedImages.IMG_TOOL_NEW_WIZARD));
	}

	@Override
	protected void doRun(IProgressMonitor progressMonitor) {
		selectedEP = getSelectedEditPart();

		if (null != selectedEP) {
			// Not allow to add multiple root elements
			if (!selectedEP.getChildren().isEmpty() && selectedEP instanceof InputEditPart) {
				MessageDialog.openWarning(Display.getCurrent().getActiveShell(),
						ERROR_ADDING_MULTIPLE_ROOT_ELEMENTS_TITLE, ERROR_ADDING_MULTIPLE_ROOT_ELEMENTS);
			} else {

				AddNewObjectDialog rootElementDialog = new AddNewObjectDialog(Display.getCurrent().getActiveShell(),
						new Class[] { IRegistryFile.class });
				rootElementDialog.create();
				rootElementDialog.setTitle(DIALOG_TITLE);
				rootElementDialog.setVisibility(DIALOG_TITLE);
				rootElementDialog.setType(DIALOG_TITLE);
				rootElementDialog.open();

				EList<PropertyKeyValuePair> propertyValueList = new BasicEList<PropertyKeyValuePair>();

				if (rootElementDialog.getTitle() != null && rootElementDialog.getSchemaType() != null) {
					// Returns the TreeNodeImpl object respective to selectedEP
					EObject object = ((Node) selectedEP.getModel()).getElement();

					TreeNode treeNodeNew = DataMapperFactory.eINSTANCE.createTreeNode();
					if (StringUtils.isNotEmpty(rootElementDialog.getTitle())) {
						treeNodeNew.setName(rootElementDialog.getTitle());
					}
					treeNodeNew.setLevel(1);
					if (StringUtils.isNotEmpty(rootElementDialog.getSchemaType())) {
						setPropertyKeyValuePairforTreeNodes(treeNodeNew, propertyValueList, JSON_SCHEMA_TYPE,
								rootElementDialog.getSchemaType());
					}
					if (StringUtils.isNotEmpty(rootElementDialog.getSchemaValue())) {
						setPropertyKeyValuePairforTreeNodes(treeNodeNew, propertyValueList, JSON_SCHEMA_SCHEMA_VALUE,
								rootElementDialog.getSchemaValue());
					}
					if (StringUtils.isNotEmpty(rootElementDialog.getID())) {
						setPropertyKeyValuePairforTreeNodes(treeNodeNew, propertyValueList, JSON_SCHEMA_ID,
								rootElementDialog.getID());
					}
					if (StringUtils.isNotEmpty(rootElementDialog.getRequired())) {
						setPropertyKeyValuePairforTreeNodes(treeNodeNew, propertyValueList, JSON_SCHEMA_REQUIRED,
								rootElementDialog.getRequired());
					}
					if (StringUtils.isNotEmpty(rootElementDialog.getNamespaces())) {
						setPropertyKeyValuePairforTreeNodes(treeNodeNew, propertyValueList, JSON_SCHEMA_NAMESPACES,
								rootElementDialog.getNamespaces());
					}
					String selectedInputOutputEditPart = getSelectedInputOutputEditPart();
					if (null != selectedInputOutputEditPart) {
						AddCommand addCmd;
						if (INPUT_EDITPART.equals(selectedInputOutputEditPart)) {
							/*
							 * add command is changed to input tree node type
							 * when input editpart is selected. index 0 to add
							 * as the first child
							 */
							addCmd = new AddCommand(((GraphicalEditPart) selectedEP).getEditingDomain(), object,
									DataMapperPackage.Literals.INPUT__TREE_NODE, treeNodeNew, 0);
						} else {
							/*
							 * add command is changed to output tree node type
							 * when output editpart is selected. index 0 to add
							 * as the first child
							 */
							addCmd = new AddCommand(((GraphicalEditPart) selectedEP).getEditingDomain(), object,
									DataMapperPackage.Literals.OUTPUT__TREE_NODE, treeNodeNew, 0);
						}

						if (addCmd.canExecute()) {
							((GraphicalEditPart) selectedEP).getEditingDomain().getCommandStack().execute(addCmd);
						}

						// FIXME force refresh root
						if (null != selectedInputOutputEditPart) {
							if (selectedEP instanceof InputEditPart) {
								DataMapperRootEditPart rep = (DataMapperRootEditPart) selectedEP.getParent();
								DataMapperRoot rootDiagram = (DataMapperRoot) ((DiagramImpl) rep.getModel())
										.getElement();
								if (INPUT_EDITPART.equals(selectedInputOutputEditPart)) {
									EList<TreeNode> inputTreeNodesList = rootDiagram.getInput().getTreeNode();
									if (null != inputTreeNodesList && !inputTreeNodesList.isEmpty()) {
										// keep a temp reference
										TreeNodeImpl inputTreeNode = (TreeNodeImpl) inputTreeNodesList.get(0);
										// remove and add to rectify placing
										RemoveCommand rootRemCmd = new RemoveCommand(
												((GraphicalEditPart) selectedEP).getEditingDomain(),
												rootDiagram.getInput(), DataMapperPackage.Literals.INPUT__TREE_NODE,
												inputTreeNode);
										if (rootRemCmd.canExecute()) {
											((GraphicalEditPart) selectedEP).getEditingDomain().getCommandStack()
													.execute(rootRemCmd);
										}

										AddCommand rootAddCmd = new AddCommand(
												((GraphicalEditPart) selectedEP).getEditingDomain(),
												rootDiagram.getInput(), DataMapperPackage.Literals.INPUT__TREE_NODE,
												inputTreeNode, 0);
										if (rootAddCmd.canExecute()) {
											((GraphicalEditPart) selectedEP).getEditingDomain().getCommandStack()
													.execute(rootAddCmd);
										}
									}
								} else {
									EList<TreeNode> outputTreeNodesList = rootDiagram.getOutput().getTreeNode();
									if (null != outputTreeNodesList && !outputTreeNodesList.isEmpty()) {
										// keep a temp reference
										TreeNodeImpl outputTreeNode = (TreeNodeImpl) outputTreeNodesList.get(0);
										// remove and add to rectify placing
										RemoveCommand rootRemCmd = new RemoveCommand(
												((GraphicalEditPart) selectedEP).getEditingDomain(),
												rootDiagram.getOutput(), DataMapperPackage.Literals.OUTPUT__TREE_NODE,
												outputTreeNode);
										if (rootRemCmd.canExecute()) {
											((GraphicalEditPart) selectedEP).getEditingDomain().getCommandStack()
													.execute(rootRemCmd);
										}

										AddCommand rootAddCmd = new AddCommand(
												((GraphicalEditPart) selectedEP).getEditingDomain(),
												rootDiagram.getOutput(), DataMapperPackage.Literals.OUTPUT__TREE_NODE,
												outputTreeNode, 0);
										if (rootAddCmd.canExecute()) {
											((GraphicalEditPart) selectedEP).getEditingDomain().getCommandStack()
													.execute(rootAddCmd);
										}

									}
								}
							}

						}

					} else {
						log.error(ERROR_ADDING_NEW_CHILD);
						MessageDialog.openError(Display.getCurrent().getActiveShell(), ERROR, ERROR_ADDING_NEW_CHILD);
						return;
					}
				}

			}

		}
	}

	private String getSelectedInputOutputEditPart() {
		EditPart tempEP = selectedEP;
		while (!(tempEP instanceof InputEditPart) && !(tempEP instanceof OutputEditPart)) {
			tempEP = tempEP.getParent();
		}

		if (tempEP instanceof InputEditPart) {
			return INPUT_EDITPART;
		} else if (tempEP instanceof OutputEditPart) {
			return OUTPUT_EDITPART;
		} else {
			// When the selected editpart is not InputEditPart or OutputEditPart
			return null;
		}
	}

	private EditPart getSelectedEditPart() {
		IStructuredSelection selection = getStructuredSelection();
		if (selection.size() == 1) {
			Object selectedEP = selection.getFirstElement();
			if (selectedEP instanceof EditPart) {
				return (EditPart) selectedEP;
			}
		}
		// In case of selecting the wrong editpart
		return null;
	}

	@Override
	public void refresh() {
		// refresh action. Does not do anything
	}

	/**
	 * Sets the key value pair for tree nodes
	 * 
	 * @param treeNode
	 *            tree node
	 * @param propertyValueList
	 *            list
	 * @param key
	 *            key
	 * @param value
	 *            value
	 */
	private void setPropertyKeyValuePairforTreeNodes(TreeNode treeNode, EList<PropertyKeyValuePair> propertyValueList,
			String key, String value) {
		PropertyKeyValuePair keyValuePair = DataMapperFactory.eINSTANCE.createPropertyKeyValuePair();
		if (treeNode.getProperties().size() > 0) {
			// If the key is already there add the new value
			if (treeNode.getProperties().contains(key)) {
				for (PropertyKeyValuePair keyValue : treeNode.getProperties()) {
					if (keyValue.getKey().equals(key)) {
						keyValue.setValue(value);
						propertyValueList.add(keyValue);
					}
				}
			} else {
				// If the key is not there add a new key value
				keyValuePair.setKey(key);
				keyValuePair.setValue(value);
				propertyValueList.add(keyValuePair);

			}
			treeNode.getProperties().addAll(propertyValueList);
		} else {
			// Initially if there are no properties add the initial property
			keyValuePair.setKey(key);
			keyValuePair.setValue(value);
			propertyValueList.add(keyValuePair);
			treeNode.getProperties().addAll(propertyValueList);
		}
	}
}