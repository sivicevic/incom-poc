package hr.incom.service.rest;

import javax.ws.rs.core.Response;

public interface IIntegrationRestWS
{


	Response processMessage(String name);

}
