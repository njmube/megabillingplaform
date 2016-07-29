(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('SettingsController', SettingsController);

    SettingsController.$inject = ['$scope', 'Principal', 'Auth','DataUtils', 'JhiLanguageService', '$translate'];

    function SettingsController ($scope, Principal, Auth, DataUtils, JhiLanguageService, $translate) {
        var vm = this;

        vm.error = null;
        vm.save = save;
        vm.settingsAccount = null;
        vm.success = null;

        vm.genders = [{code:'M', name: 'global.form.gender.male'}, {code: 'F', name: 'global.form.gender.female'}];
        vm.current_gender = null;

        vm.langs = [{key:'en', name: 'settings.form.language.english'}, {key: 'es', name: 'settings.form.language.spanish'}];
        vm.current_lang = null;

        /**
         * Store the "settings account" in a separate variable, and not in the shared "account" variable.
         */
        var copyAccount = function (account) {
            return {
                activated: account.activated,
                rfc: account.rfc,
                email: account.email,
                name: account.name,
                firtsurname: account.firtsurname,
                secondsurname: account.secondsurname,
                langKey: account.langKey,
                phone: account.phone,
                gender: account.gender,
                login: account.login,
                creator: account.creator,
                path_photo: account.path_photo,
                filephoto: account.filephoto,
                filephotoContentType: account.filephotoContentType
            };
        };

        vm.onGenderChange = function(){
            if(vm.current_gender != null){
                vm.settingsAccount.gender = vm.current_gender.code;
            }
            else{
                vm.settingsAccount.gender = null;
            }
        };

        vm.onLangChange = function(){
            if(vm.current_lang != null){
                vm.settingsAccount.langKey = vm.current_lang.key;
            }
            else{
                vm.settingsAccount.langKey = null;
            }
        };

        Principal.identity().then(function(account) {
            vm.settingsAccount = copyAccount(account);

            if(vm.settingsAccount.gender == 'M'){
                vm.current_gender =  vm.genders[0];
            }
            else if(vm.settingsAccount.gender == 'F'){
                vm.current_gender =  vm.genders[1];
            }

            if(vm.settingsAccount.langKey == 'en'){
                vm.current_lang =  vm.langs[0];
            }
            else if(vm.settingsAccount.langKey == 'es'){
                vm.current_lang =  vm.langs[1];
            }
        });

        function save () {
            Auth.updateAccount(vm.settingsAccount).then(function() {
                vm.error = null;
                vm.success = 'OK';

                Principal.identity(true).then(function(account) {
                    vm.settingsAccount = copyAccount(account);
                });

                JhiLanguageService.getCurrent().then(function(current) {
                    if (vm.settingsAccount.langKey !== current) {
                        $translate.use(vm.settingsAccount.langKey);
                    }
                });

            }).catch(function() {
                vm.success = null;
                vm.error = 'ERROR';
            });
        }

        vm.setPhoto = function ($file, settingsAccount) {
            if ($file) {
                vm.photo_file = $file;
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {

                        if($file.size <= 10485760 && ($file.type == "image/png" || $file.type == "image/jpeg") ){
                            vm.settingsAccount.path_photo = $file.name;
                            settingsAccount.filephoto = base64Data;
                            settingsAccount.filephotoContentType = $file.type;
                            vm.messphoto = null;
                        }
                        else{
                            vm.messphoto = true;
                        }
                    });
                });
            }
        };

        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

    }
})();
