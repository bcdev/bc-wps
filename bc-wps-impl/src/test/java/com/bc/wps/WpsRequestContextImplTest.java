package com.bc.wps;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * @author hans
 */
public class WpsRequestContextImplTest {

    private HttpServletRequest mockServletRequest;
    private WpsRequestContextImpl requestContext;
    private Principal mockPrincipal;

    @Before
    public void setUp() throws Exception {
        mockServletRequest = mock(HttpServletRequest.class);
        mockPrincipal = mock(Principal.class);
    }

    @Test
    public void testGetUserName() throws Exception {
        when(mockPrincipal.getName()).thenReturn("mockName");
        when(mockServletRequest.getUserPrincipal()).thenReturn(mockPrincipal);

        requestContext = new WpsRequestContextImpl(mockServletRequest);

        assertThat(requestContext.getUserName(), equalTo("mockName"));
    }

    @Test
    public void testGetEmptyUserName() throws Exception {
        when(mockPrincipal.getName()).thenReturn("");
        when(mockServletRequest.getUserPrincipal()).thenReturn(mockPrincipal);

        requestContext = new WpsRequestContextImpl(mockServletRequest);

        assertThat(requestContext.getUserName(), equalTo(""));
    }
}