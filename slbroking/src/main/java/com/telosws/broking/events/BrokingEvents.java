package com.telosws.broking.events;

import com.telosws.broking.ui.util.BrokingHomeNavigationViewList;
import com.telosws.broking.ui.util.PolicyAdminNavigationViewList;
import com.telosws.orm.tables.pojos.User;

/**
 * 
 * @author Harish Kalepu
 *
 */

public abstract class BrokingEvents {

	public static final class PostViewChangeEvent {
        private final PolicyAdminNavigationViewList view;

        public PostViewChangeEvent(final PolicyAdminNavigationViewList view) {
            this.view = view;
        }

        public PolicyAdminNavigationViewList getView() {
            return view;
        }
    }
	
	public static final class ViewChangeEvent {
        private final BrokingHomeNavigationViewList view;

        public ViewChangeEvent(final BrokingHomeNavigationViewList view) {
            this.view = view;
        }

        public BrokingHomeNavigationViewList getView() {
            return view;
        }
    }

    /*public static final class ViewChangeEvent {
        private final HRViewList hrView;

        public ViewChangeEvent(final HRViewList view) {
            this.hrView = view;
        }

        public HRViewList getView() {
            return hrView;
        }
    }*/
	public static final class BackToHomeEvent {
		private final User user;

		public BackToHomeEvent(final User user) {
	            this.user = user;
	        }

		public User getUser() {
			return user;
		}
    }

    public static final class UserUpdateEvent {
        private final User user;

        public UserUpdateEvent(final User user) {
            this.user = user;
        }

        public User getUser() {
            return user;
        }

    }

    public static final class PostReviewViewChangeEvent {
        private final PolicyAdminNavigationViewList view;

        public PostReviewViewChangeEvent(final PolicyAdminNavigationViewList view) {
            this.view = view;
        }

        public PolicyAdminNavigationViewList getView() {
            return view;
        }
    }
}
