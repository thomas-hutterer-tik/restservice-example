package restservice.domain.imagerecognition;

public class Prediction {

	private String label;
	private String description;
	private Double probability;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getProbability() {
		return probability;
	}

	public void setProbability(Double probability) {
		this.probability = probability;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Prediction [");
		if (label != null)
			builder.append("label=").append(label).append(", ");
		if (description != null)
			builder.append("description=").append(description).append(", ");
		if (probability != null)
			builder.append("probability=").append(probability);
		builder.append("]");
		return builder.toString();
	}

}
