/*
*  Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/

package org.wso2.developerstudio.eclipse.gmf.esb.diagram.custom.deserializer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMAttribute;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMNamespace;
import org.apache.synapse.task.TaskDescription;

public class TaskDescriptionExtFactory {

    private static final String NULL_NAMESPACE = "";
    private final static String TASK = "task";
    private final static String TRIGGER = "trigger";
    private final static String PROPERTY = "property";
    private static final String DESCRIPTION = "description";

    public static TaskDescription createTaskDescription(OMElement element, OMNamespace tagetNamespace) {

        TaskDescription taskDescription = new TaskDescription();

        String name = element.getAttributeValue(new QName(NULL_NAMESPACE, "name"));
        if (name != null) {
            taskDescription.setName(name);
        } else {
            taskDescription.setName("");
        }

        String group = element.getAttributeValue(new QName(NULL_NAMESPACE, "group"));
        if (group != null) {
            taskDescription.setTaskGroup(group);
        }

        // set the task class
        OMAttribute classAttr = element.getAttribute(new QName("class"));
        if (classAttr != null && classAttr.getAttributeValue() != null) {
            String classname = classAttr.getAttributeValue();
            taskDescription.setTaskImplClassName(classname);
        } else {
            taskDescription.setTaskImplClassName("org.apache.synapse.startup.tasks.MessageInjector");
        }

        OMElement descElem = element.getFirstChildWithName(createQName(DESCRIPTION, tagetNamespace));
        if (descElem != null) {
            taskDescription.setTaskDescription(descElem.getText());
        }

        // set pinned server list
        OMAttribute pinnedServers = element.getAttribute(new QName(NULL_NAMESPACE, "pinnedServers"));
        if (pinnedServers != null) {
            String pinnedServersValue = pinnedServers.getAttributeValue();
            if (pinnedServersValue == null) {
            } else {
                StringTokenizer st = new StringTokenizer(pinnedServersValue, " ,");
                List<String> pinnedServersList = new ArrayList<String>();
                while (st.hasMoreTokens()) {
                    String token = st.nextToken();
                    if (token.length() != 0) {
                        pinnedServersList.add(token);
                    }
                }
                taskDescription.setPinnedServers(pinnedServersList);
            }
        }

        // next sort out the property children
        Iterator it = element.getChildrenWithName(createQName(PROPERTY, tagetNamespace));
        while (it.hasNext()) {
            taskDescription.setXmlProperty((OMElement) it.next());
        }

        // setting the trigger to the task
        OMElement trigger = element.getFirstChildWithName(createQName(TRIGGER, tagetNamespace));
        if (trigger != null) {

            OMAttribute count = trigger.getAttribute(new QName("count"));
            if (count != null) {
                try {
                    taskDescription.setCount(Integer.parseInt(count.getAttributeValue()));
                } catch (Exception e) {
                    taskDescription.setCount(1);
                }
            }

            OMAttribute once = trigger.getAttribute(new QName("once"));
            if (once != null && Boolean.TRUE.toString().equals(once.getAttributeValue())) {
                taskDescription.setCount(1);
                taskDescription.setInterval(1);
            }

            OMAttribute repeatInterval = trigger.getAttribute(new QName("interval"));
            if (repeatInterval != null && repeatInterval.getAttributeValue() != null) {
                try {
                    long repeatIntervalInMillis = Long.parseLong(repeatInterval.getAttributeValue());
                    taskDescription.setInterval(repeatIntervalInMillis);
                } catch (Exception e) {
                    taskDescription.setInterval(1000);
                }
            }

            OMAttribute expr = trigger.getAttribute(new QName("cron"));
            if (expr == null && taskDescription.getInterval() == 0) {
                taskDescription.setCount(1);
                taskDescription.setInterval(1);
            } else if (expr != null && expr.getAttributeValue() != null) {
                taskDescription.setCronExpression(expr.getAttributeValue());
            }

        } else {
            taskDescription.setCount(1);
            taskDescription.setInterval(1);
        }
        return taskDescription;
    }

    private static QName createQName(String localName, OMNamespace omNamespace) {
        return new QName(omNamespace.getNamespaceURI(), localName, omNamespace.getPrefix());
    }
}
