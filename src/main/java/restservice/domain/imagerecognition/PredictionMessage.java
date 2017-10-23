package restservice.domain.imagerecognition;

import java.util.Arrays;

public class PredictionMessage {

	private Prediction[] predictions;

	public Prediction[] getPredictions() {
		return predictions;
	}

	public void setPredictions(Prediction[] predictions) {
		this.predictions = predictions;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PredictionMessage [");
		if (predictions != null)
			builder.append("predictions=").append(Arrays.toString(predictions));
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
