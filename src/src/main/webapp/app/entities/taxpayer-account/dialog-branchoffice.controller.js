(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('DialogBranchofficeController', DialogBranchofficeController);

    DialogBranchofficeController.$inject = ['$scope', '$filter','$stateParams', '$uibModal', 'Branch_office', 'Taxpayer_account', '$uibModalInstance', '$q', 'entity', 'DataUtils', 'Tax_regime', 'C_country', 'C_state', 'C_municipality', 'C_colony', 'C_zip_code','Type_taxpayer'];

    function DialogBranchofficeController ($scope, $filter, $stateParams, $uibModal, Branch_office, Taxpayer_account, $uibModalInstance, $q, entity, DataUtils, Tax_regime, C_country, C_state, C_municipality, C_colony, C_zip_code, Type_taxpayer) {
        var vm = this;

        vm.branch_office = entity;
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
        vm.clicEdit = clicEdit;

        vm.load = function(id) {
            Taxpayer_account.get({id : id}, function(result) {
                vm.taxpayer_account = result;
            });
        };

        function clicEdit(){
            vm.edit = 'OK';
        }

        function add(){
            vm.isSaving = true;
            Branch_office.update(vm.branch_office, onSaveSuccess, onSaveError);
        }

        var onSaveSuccess = function (result) {
            vm.showInfo = false;
            $uibModalInstance.close({});
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };


        vm.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };

        function onChangeC_country () {
            var countryId = vm.branch_office.tax_address.c_country.id;
            C_state.query({countryId: countryId, filtername:" "}, function(result){
                vm.c_states = result;
            });
        }

        function onChangeC_state () {
            var stateId = vm.branch_office.tax_address.c_state.id;
            C_municipality.query({stateId: stateId, filtername:" "}, function(result){
                vm.c_municipalitys = result;
            });
        }

        function onChangeC_municipality () {
            var municipalityId = vm.branch_office.tax_address.c_municipality.id;
            C_colony.query({municipalityId: municipalityId, filtername:" "}, function(result){
                vm.c_colonys = result;
            });
        }

        function onChangeC_colony(){
            C_zip_code.get({id : vm.branch_office.tax_address.c_colony.c_zip_code.id}, function(result) {
                vm.branch_office.tax_address.c_zip_code = result;
            });
        }

    }
})();




