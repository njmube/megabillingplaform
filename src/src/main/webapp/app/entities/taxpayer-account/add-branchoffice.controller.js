(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('AddBranchofficeController', AddBranchofficeController);

    AddBranchofficeController.$inject = ['$scope', '$filter','$stateParams', '$uibModal', 'Branch_office', 'Taxpayer_account', '$uibModalInstance', '$q', 'entity', 'DataUtils', 'Tax_regime', 'C_country', 'C_state', 'C_municipality', 'C_colony', 'C_zip_code','Type_taxpayer'];

    function AddBranchofficeController ($scope, $filter, $stateParams, $uibModal, Branch_office, Taxpayer_account, $uibModalInstance, $q, entity, DataUtils, Tax_regime, C_country, C_state, C_municipality, C_colony, C_zip_code, Type_taxpayer) {
        var vm = this;

        vm.taxpayer_account = entity;
        vm.showInfo = false;
        vm.edit = null;
        vm.c_countrys = C_country.query({pg:1, filtername:" "});
        vm.c_states = C_state.query({countryId:151, filtername:" "});
        vm.c_municipalitys = null;
        vm.c_colonys = null;
        vm.tax_address = {};

        vm.add = add;
        vm.onChangeC_country = onChangeC_country;
        vm.onChangeC_state = onChangeC_state;
        vm.onChangeC_municipality = onChangeC_municipality;
        vm.onChangeC_colony = onChangeC_colony;

        vm.load = function(id) {
            Taxpayer_account.get({id : id}, function(result) {
                vm.taxpayer_account = result;
            });
        };

        function add(){
            vm.isSaving = true;
            vm.branch_office.taxpayer_account = vm.taxpayer_account;
            vm.branch_office.tax_address = vm.tax_address;
            if (vm.branch_office.id !== null) {
                Branch_office.update(vm.branch_office, onSaveSuccess, onSaveError);
            } else {
                Branch_office.save(vm.branch_office, onSaveSuccess, onSaveError);
            }
        }

        var onSaveSuccess = function (result) {
            vm.showInfo = false;
            $uibModalInstance.close({
                taxpayer_account: vm.taxpayer_account
            });
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };


        vm.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };

        function onChangeC_country () {
            var countryId = vm.tax_address.c_country.id;
            C_state.query({countryId: countryId, filtername:" "}, function(result){
                vm.c_states = result;
            });
        }

        function onChangeC_state () {
            var stateId = vm.tax_address.c_state.id;
            C_municipality.query({stateId: stateId, filtername:" "}, function(result){
                vm.c_municipalitys = result;
            });
        }

        function onChangeC_municipality () {
            var municipalityId = vm.tax_address.c_municipality.id;
            C_colony.query({municipalityId: municipalityId, filtername:" "}, function(result){
                vm.c_colonys = result;
            });
        }

        function onChangeC_colony(){
            C_zip_code.get({id : vm.tax_address.c_colony.c_zip_code.id}, function(result) {
                vm.tax_address.c_zip_code = result;
            });
        }

    }
})();



