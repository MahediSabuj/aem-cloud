package com.aem.cloud.core.servlets;

import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import com.adobe.granite.ui.components.ds.ValueMapResource;
import com.day.cq.commons.jcr.JcrConstants;
import org.apache.commons.collections4.iterators.TransformIterator;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;

import javax.servlet.Servlet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component(service = { Servlet.class })
@SlingServletPaths(
    value = "/bin/public/aem-cloud/dropdowns"
)
@ServiceDescription("Simple Demo Servlet")
public class DropdownServlet extends SlingSafeMethodsServlet {
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) {
        ResourceResolver resolver = request.getResourceResolver();
        String currentPagePath = request.getRequestPathInfo().getSuffix();

        Resource datasource = request.getResource().getChild("datasource");
        ValueMap vm = datasource.getValueMap();
        String tagProperties = vm.get("tagProperties", "");

        List<String> options = new ArrayList<>();
        options.add("AEM");

        DataSource dataSource = new SimpleDataSource(new TransformIterator<>(options.iterator(), input -> {
            ValueMap valueMap = new ValueMapDecorator(new HashMap<>());
            valueMap.put("value", input.toLowerCase());
            valueMap.put("text", input);

            return new ValueMapResource(resolver, new ResourceMetadata(), JcrConstants.NT_UNSTRUCTURED, valueMap);
        }));

        request.setAttribute(DataSource.class.getName(), dataSource);
    }
}
