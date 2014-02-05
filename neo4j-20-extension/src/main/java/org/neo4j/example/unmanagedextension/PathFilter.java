package org.neo4j.example.unmanagedextension;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.traversal.Evaluation;
import org.neo4j.graphdb.traversal.Evaluator;

public class PathFilter implements Evaluator, Predicate {
	Long ultimoMovimento = 0L
			, start = 0L
	 		, end = 0L;
	public PathFilter(Long start, Long end) {
		this.start = start;
		this.end = end;
	}
	public Evaluation evaluate(Path path) {
		ultimoMovimento = start;
		for (Relationship rel : path.relationships()) {
			Long dataMovimento = (Long) rel.getProperty("data_movimento_ts");
			if (evaluateDate(dataMovimento, ultimoMovimento, end)) {
				ultimoMovimento = dataMovimento;
				return Evaluation.INCLUDE_AND_CONTINUE;
			}
			ultimoMovimento = dataMovimento;
		}
		return CollectionUtils.size(path.relationships()) > 0 ? Evaluation.EXCLUDE_AND_PRUNE : Evaluation.EXCLUDE_AND_CONTINUE;
	}
	public boolean evaluate(Object arg) {
		if (!(arg instanceof Relationship)) {
			return false;
		}
		Relationship rel = (Relationship) arg;
		Long dataMovimento = (Long) rel.getProperty("data_movimento_ts");
		if (evaluateDate(dataMovimento, ultimoMovimento, end)) {
			ultimoMovimento = dataMovimento;
			return true;
		}
		ultimoMovimento = dataMovimento;
		return false;
	}
	
	private boolean evaluateDate(Long dtMov, Long start, Long end) {
		return dtMov >= start && dtMov <= end;
	}
}
