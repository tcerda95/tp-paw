package tp.paw.khet.persistence.querybuilder;

import java.util.Map;
import java.util.Set;

public interface KeywordQueryBuilder extends QueryBuilder {
	String buildQuery(final Set<String> keywords, final Map<String, String> keyWordsRegExp);
}
