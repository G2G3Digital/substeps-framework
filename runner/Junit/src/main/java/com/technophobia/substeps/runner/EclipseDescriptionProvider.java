/*
 *	Copyright Technophobia Ltd 2012
 *
 *   This file is part of Substeps.
 *
 *    Substeps is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    Substeps is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with Substeps.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.technophobia.substeps.runner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.technophobia.substeps.model.Configuration;
import com.technophobia.substeps.runner.description.DescriptionBuilder;
import com.technophobia.substeps.runner.description.DescriptorStatus;
import com.technophobia.substeps.runner.description.JunitVersionedDescriptionBuilder;
import org.junit.Assert;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.technophobia.substeps.execution.node.IExecutionNode;
import com.technophobia.substeps.execution.node.NodeWithChildren;
import com.technophobia.substeps.report.ExecutionReportBuilder;

/**
 * @author ian
 * 
 */
public class EclipseDescriptionProvider implements DescriptionProvider {

    private final Logger log = LoggerFactory.getLogger(EclipseDescriptionProvider.class);

    private final DescriptionBuilder descriptionBuilder = new JunitVersionedDescriptionBuilder();

    private static final String STEP_DEPTH_KEY = "step.depth.description";

    static {
        Configuration.INSTANCE.addDefaultProperty(STEP_DEPTH_KEY, Integer.valueOf(5));
    }

    public Map<Long, Description> buildDescriptionMap(final IExecutionNode rootNode, final Class<?> classContainingTheTests) {
        final Description rootDescription = Description.createSuiteDescription(classContainingTheTests);

        final Map<Long, Description> descriptionMap = new HashMap<Long, Description>();

        descriptionMap.put(Long.valueOf(rootNode.getId()), rootDescription);

        final DescriptorStatus status = new DescriptorStatus();

        if (rootNode instanceof NodeWithChildren &&  ((NodeWithChildren<?>)rootNode).hasChildren()) {
            for (final IExecutionNode child : ((NodeWithChildren<?>)rootNode).getChildren()) {
                rootDescription.addChild(buildDescription(child, descriptionMap, status));
            }
        }

        return descriptionMap;
    }

    private Description buildDescription(final IExecutionNode node, final Map<Long, Description> descriptionMap,
            final DescriptorStatus status) {
        final Description des = buildDescription(node, status);

        if (node instanceof NodeWithChildren) {

            NodeWithChildren<?> nodeWithChildren = (NodeWithChildren<?>) node;

            if (nodeWithChildren.hasChildren() && nodeWithChildren.getDepth() < Configuration.INSTANCE.getInt(STEP_DEPTH_KEY)) {

                for (final IExecutionNode child : nodeWithChildren.getChildren()) {

                    final Description childDescription = buildDescription(child, descriptionMap, status);
                    if (childDescription != null) {
                        des.addChild(childDescription);
                    }
                }
            }
        }
        descriptionMap.put(Long.valueOf(node.getId()), des);

        return des;
    }

    private Description buildDescription(IExecutionNode node, DescriptorStatus status) {
        return descriptionBuilder.descriptionFor(node, status);
    }

    /**
     * @param node
     * @return
     */

}
