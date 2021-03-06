package com;

/**
 * This class was automatically generated by the data modeler tool.
 */

public class eventAnalysis implements java.io.Serializable {

	static final long serialVersionUID = 1L;

	@org.kie.api.definition.type.Label("eventEffectiveness")
	private String eventEffectiveness;
	@org.kie.api.definition.type.Label("eventResponsePayload")
	private String eventResponsePayload;

	public eventAnalysis() {
	}

	public String getEventEffectiveness() {
		return this.eventEffectiveness;
	}

	public void setEventEffectiveness(String eventEffectiveness) {
		this.eventEffectiveness = eventEffectiveness;
	}

	public String getEventResponsePayload() {
		return this.eventResponsePayload;
	}

	public void setEventResponsePayload(String eventResponsePayload) {
		this.eventResponsePayload = eventResponsePayload;
	}

	public eventAnalysis(String eventEffectiveness,
			String eventResponsePayload) {
		this.eventEffectiveness = eventEffectiveness;
		this.eventResponsePayload = eventResponsePayload;
	}

	@Override
	public String toString() {
		return "eventAnalysis{" +
				"eventEffectiveness='" + eventEffectiveness + '\'' +
				", eventResponsePayload='" + eventResponsePayload + '\'' +
				'}';
	}
}