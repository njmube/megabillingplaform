(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Request_taxpayer_accountAfilitatedController', Request_taxpayer_accountAfilitatedController);

    Request_taxpayer_accountAfilitatedController.$inject = ['$timeout', '$filter','Principal', '$scope', '$stateParams', 'C_country','C_state', 'C_municipality', 'C_colony', 'C_zip_code', 'Request_taxpayer_account', 'Request_state', 'Tax_address_request'];

    function Request_taxpayer_accountAfilitatedController ($timeout, $filter, Principal, $scope, $stateParams, C_country, C_state, C_municipality, C_colony, C_zip_code, Request_taxpayer_account, Request_state, Tax_address_request) {
        var vm = this;

        vm.datePickerOpenStatus = {};
        vm.request_taxpayer_account = {};
        vm.openCalendar = openCalendar;
        vm.c_countrys = C_country.query({pg:1, filtername:" "});
        vm.c_states = C_state.query({countryId:151, filtername:" "});
        vm.c_municipalitys = null;
        vm.c_colonys = null;
        vm.save = save;
        vm.account = null;
        vm.propio = null;
        vm.isSaving = null;
        vm.loadRequest = function () {
            Request_state.get({id: 1}, function(result) {
                vm.request_state = result;
            });
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.request_taxpayer_account.name = vm.account.name;
                vm.request_taxpayer_account.firtsurname = vm.account.firtsurname;
                vm.request_taxpayer_account.secondsurname = vm.account.secondsurname;
                vm.request_taxpayer_account.phone = vm.account.phone;
                vm.request_taxpayer_account.email = vm.account.email;
                if(vm.account.gender == 'M'){
                    vm.current_gender =  vm.genders[0];
                    vm.request_taxpayer_account.gender = vm.current_gender.code;
                }
                else if(vm.account.gender == 'F'){
                    vm.current_gender =  vm.genders[1];
                    vm.request_taxpayer_account.gender = vm.current_gender.code;
                }
                vm.request_taxpayer_account.rfc = vm.account.rfc;
                vm.request_taxpayer_account.bussinesname = vm.account.login;
                vm.request_taxpayer_account.accountemail = vm.account.email;
            });
        };

        vm.loadRequest();
        //vm.tax_address_request = Tax_address_request.get({id : 0});

        vm.onChangeC_country = onChangeC_country;
        vm.onChangeC_state = onChangeC_state;
        vm.onChangeC_municipality = onChangeC_municipality;
        vm.onChangeC_colony = onChangeC_colony;

        vm.genders = [{code:'M', name: 'global.form.gender.male'}, {code: 'F', name: 'global.form.gender.female'}];

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });



        vm.onGenderChange = function(){
            if(vm.current_gender != null){
                vm.request_taxpayer_account.gender = vm.current_gender.code;
            }
            else{
                vm.request_taxpayer_account.gender = null;
            }
        };

        function onChangeC_country () {
            var countryId = vm.tax_address_request.c_country.id;
            C_state.query({countryId: countryId, filtername:" "}, function(result){
                vm.c_states = result;
            });
        }

        function onChangeC_state () {
            var stateId = vm.tax_address_request.c_state.id;
            C_municipality.query({stateId: stateId, filtername:" "}, function(result){
                vm.c_municipalitys = result;
            });
        }

        function onChangeC_municipality () {
            var municipalityId = vm.tax_address_request.c_municipality.id;
            C_colony.query({municipalityId: municipalityId, filtername:" "}, function(result){
                vm.c_colonys = result;
            });
        }

        function onChangeC_colony(){
            C_zip_code.get({id : vm.tax_address_request.c_colony.c_zip_code.id}, function(result) {
                vm.tax_address_request.c_zip_code = result;
            });
        }

        function save () {

            vm.request_taxpayer_account.request_state = vm.request_state;
            vm.request_taxpayer_account.tax_address_request = vm.tax_address_request;

            if (vm.request_taxpayer_account.id !== null) {
                Request_taxpayer_account.update(vm.request_taxpayer_account, onSaveSuccess, onSaveError);
            } else {
                Request_taxpayer_account.save(vm.request_taxpayer_account, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            vm.isSaving = true;
            window.location.assign("#/");
        }

        function onSaveError () {
            vm.isSaving = null;
        }

        vm.datePickerOpenStatus.date_born = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();

