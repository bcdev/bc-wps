package com.bc.wps.api;


import com.bc.wps.api.schema.Capabilities;
import com.bc.wps.api.schema.Execute;
import com.bc.wps.api.schema.ExecuteResponse;
import com.bc.wps.api.schema.ProcessDescriptionType;

import java.util.List;

/**
 * This is the interface to the WPS service instance. Implement this when creating a new instance (eg. Calvalus, LC, SNAP, etc.)
 *
 * @author hans
 */
public interface WpsServiceInstance {

    /**
     * OGC WPS GetCapabilities operation.
     * Please be sure to check the WPS standard when constructing the Capabilities object
     * because there are no validity checking to the returned Capabilities object.
     * For more information about the standard, @see <a href="http://portal.opengeospatial.org/files/?artifact_id=24151">
     *     http://portal.opengeospatial.org/files/?artifact_id=24151</a>
     *
     * @param context Context information that is extracted from the request.
     * @return Capabilities object
     * @throws WpsServiceException when capabilities cannot be returned
     */
    Capabilities getCapabilities(WpsRequestContext context) throws WpsServiceException;

    /**
     * OGC WPS DescribeProcess operation.
     * For more information about the standard, @see <a href="http://portal.opengeospatial.org/files/?artifact_id=24151">
     *     http://portal.opengeospatial.org/files/?artifact_id=24151</a>
     *
     * @param context Context information that is extracted from the request.
     * @param processId To select the process(es) to be described. For multiple processes, individual processId will come
     *                  one after another, separated by comma. For example: process1,process2,process3. For a request to
     *                  describe all available processes, the value in this parameter will be "<b>all</b>"
     * @return List of ProcessDescriptionType
     * @throws WpsServiceException when list of ProcessDescriptionType cannot be returned
     */
    List<ProcessDescriptionType> describeProcess(WpsRequestContext context, String processId) throws WpsServiceException;

    /**
     * OGC WPS Execute operation.
     * For more information about the standard, @see <a href="http://portal.opengeospatial.org/files/?artifact_id=24151">
     *     http://portal.opengeospatial.org/files/?artifact_id=24151</a>
     *
     * @param context Context information that is extracted from the request.
     * @param executeRequest The incoming execute request
     * @return ExecuteResponse object that may contain one of the following states:
     * <ul>
     *     <li>a process has been accepted successfully, and is now running at the background (asynchronous only)</li>
     *     <li>a process has been processed successfully, and the result(s) are available at the provided URL (synchronous only)</li>
     *     <li>a failure has occurred</li>
     * </ul>
     * @throws WpsServiceException when there is an error with the request, the processing, or the staging
     */
    ExecuteResponse doExecute(WpsRequestContext context, Execute executeRequest) throws WpsServiceException;

    /**
     * This operation is not part of OGC WPS 1.0.0, but it is included to provide a mean to better monitor asynchronous processes.
     *
     * @param context Context information that is extracted from the request.
     * @param jobId The job ID to be queried
     * @return ExecuteResponse object that may contain one of the following states:
     * <ul>
     *     <li>a process has been started and the current progress is displayed</li>
     *     <li>a process has been processed successfully, and the result(s) are available at the provided URL</li>
     *     <li>a failure has occurred</li>
     * </ul>
     * @throws WpsServiceException when there is a problem retrieving the status of the job of the given jobId
     */
    ExecuteResponse getStatus(WpsRequestContext context, String jobId) throws WpsServiceException;

    /**
     * Called when the service is no longer used. This may occur for different reasons:
     * <ol>
     *     <li>the service provider is about to be uninstalled or updated;</li>
     *     <li>the service hasn't been used for a period of time, so it will be deactivated;</li>
     *     <li>the WPS is about to be shut down.</li>
     * </ol>
     */
    void dispose();
}
