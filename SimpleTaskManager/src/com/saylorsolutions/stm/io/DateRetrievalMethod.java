package com.saylorsolutions.stm.io;

import java.util.Date;

/**
 * This enum defines how a retrieval by Date by a TaskDataSource 
 * should compare each Task. The default method used if not specified 
 * is RETRIEVE_MATCH.
 * @author Doug Saylor
 * 
 */
public enum DateRetrievalMethod {
	RETRIEVE_MATCH {
		@Override
		public boolean matchesCriteria(Date basis, Date other) {
			if(basis != null && other != null) {
				return other.equals(basis);
			} else if(basis == null && other == null) {
				return true;
			}
			return false;
		}
	},
	RETRIEVE_AT_OR_BEFORE {
		@Override
		public boolean matchesCriteria(Date basis, Date other) {
			if(basis != null && other != null) {
				return other.compareTo(basis) <= 0;
			} else if(basis == null && other == null) {
				return true;
			}
			return basis == null;
		}
	},
	RETRIEVE_BEFORE {
		@Override
		public boolean matchesCriteria(Date basis, Date other) {
			if(basis != null && other != null) {
				return other.compareTo(basis) < 0;
			} else if(basis == null && other == null) {
				return false;
			}
			return basis == null;
		}
	},
	RETRIEVE_AT_OR_AFTER {
		@Override
		public boolean matchesCriteria(Date basis, Date other) {
			if(basis != null && other != null) {
				return other.compareTo(basis) >= 0;
			} else if(basis == null && other == null) {
				return true;
			}
			return other == null;
		}
	},
	RETRIEVE_AFTER {
		@Override
		public boolean matchesCriteria(Date basis, Date other) {
			if(basis != null && other != null) {
				return other.compareTo(basis) > 0;
			} else if(basis == null && other == null) {
				return false;
			}
			return other == null;
		}
	};
	
	public abstract boolean matchesCriteria(Date basis, Date other);
}
