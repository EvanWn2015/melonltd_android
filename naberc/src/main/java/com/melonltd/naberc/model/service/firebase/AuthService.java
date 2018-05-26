package com.melonltd.naberc.model.service.firebase;

public class AuthService {

//    public static void signInWithEmailAndPassword(String mail, String password, final FragmentManager fm, OnCompleteListener<AuthResult> listener) {
//        fm.beginTransaction().replace(R.id.frameContainer, PageFragmentFactory.of(PageType.HOME, null)).commit();
//        if (UserMainActivity.bottomMenuTabLayout != null) {
//            UserMainActivity.bottomMenuTabLayout.setVisibility(View.VISIBLE);
//        }
////        if (listener != null) {
////            BaseCore.auth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(listener);
////        } else {
////            BaseCore.auth.signInWithEmailAndPassword(mail, password)
////                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
////                        @Override
////                        public void onComplete(@NonNull Task<AuthResult> task) {
////                            if (task.isSuccessful()) {
////                                BaseCore.currentUser = BaseCore.auth.getCurrentUser();
////                                String uid = BaseCore.currentUser.getUid();
////                                SharedPreferencesService.setUserUID(uid);
////                                if (UserMainActivity.bottomMenuTabLayout != null) {
////                                    UserMainActivity.bottomMenuTabLayout.setVisibility(View.VISIBLE);
////                                }
////                                fm.beginTransaction().replace(R.id.frameContainer, PageFragmentFactory.of(PageType.HOME, null)).commit();
////                            } else {
//////                                BaseCore.LOADING_BAR.hide();
//////                                BaseCore.POPUP.show(R.string.account_does_not_exist);
////                            }
////                        }
////                    });
////        }
//
//    }
}
