<%@ page session="true" %>
<%@ page
        import="org.openid4java.OpenIDException, org.openid4java.consumer.ConsumerManager, org.openid4java.consumer.VerificationResult, org.openid4java.discovery.DiscoveryInformation, org.openid4java.discovery.Identifier, org.openid4java.message.AuthSuccess, org.openid4java.message.ParameterList" %>

<%

    ConsumerManager manager = (ConsumerManager) request.getAttribute("consumermanager");
    try {
        // --- processing the authentication response

        // extract the parameters from the authentication response
        // (which comes in as a HTTP request from the OpenID provider)
        ParameterList responselist =
                new ParameterList(request.getParameterMap());

        // retrieve the previously stored discovery information
        DiscoveryInformation discovered =
                (DiscoveryInformation) session.getAttribute("openid-disco");

        // extract the receiving URL from the HTTP request
        StringBuffer receivingURL = request.getRequestURL();
        String queryString = request.getQueryString();
        if (queryString != null && queryString.length() > 0) {
            receivingURL.append("?").append(request.getQueryString());
        }

        // verify the response; ConsumerManager needs to be the same
        // (static) instance used to place the authentication request
        VerificationResult verification = manager.verify(
                receivingURL.toString(),
                responselist, discovered);

        // examine the verification result and extract the verified identifier
        Identifier verified = verification.getVerifiedId();
        if (verified != null) {
            AuthSuccess authSuccess =
                    (AuthSuccess) verification.getAuthResponse();

            session.setAttribute("openid", authSuccess.getIdentity());
            session.setAttribute("openid-claimed", authSuccess.getClaimed());
            response.sendRedirect(".");  // success
        } else {
%>
Failed to login!
<%
    }
} catch (OpenIDException e) {
%>
Login error!
<%
        // present error to the user
    }

%>
