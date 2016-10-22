(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('AddSerieFolioController', AddSerieFolioController);

    AddSerieFolioController.$inject = ['$scope', '$filter','$stateParams', '$uibModal', 'Taxpayer_series_folio', 'Taxpayer_account', '$uibModalInstance', '$q', 'entity', 'DataUtils', 'Tax_regime', 'C_country', 'C_state', 'C_municipality', 'C_colony', 'C_zip_code','Type_taxpayer'];

    function AddSerieFolioController ($scope, $filter, $stateParams, $uibModal, Taxpayer_series_folio, Taxpayer_account, $uibModalInstance, $q, entity, DataUtils, Tax_regime, C_country, C_state, C_municipality, C_colony, C_zip_code, Type_taxpayer) {
        var vm = this;

        vm.taxpayer_account = entity;
        vm.showInfo = false;
        vm.taxpayer_series_folio = {};
        var today = new Date();
        vm.toDate = new Date(today.getFullYear(), today.getMonth(), today.getDate() + 1);
        vm.messfolio = null;

        vm.add = add;

        vm.load = function(id) {
            Taxpayer_account.get({id : id}, function(result) {
                vm.taxpayer_account = result;
            });
        };

        function add(){
            vm.isSaving = true;
            vm.messfolio = null;
            vm.taxpayer_series_folio.taxpayer_account = vm.taxpayer_account;
            vm.taxpayer_series_folio.folio_current = vm.taxpayer_series_folio.folio_start;
            vm.taxpayer_series_folio.date_creation = vm.toDate;

            if(vm.taxpayer_series_folio.folio_start <= vm.taxpayer_series_folio.folio_end ){
                Taxpayer_series_folio.save(vm.taxpayer_series_folio, onSaveSuccess, onSaveError);
            }
            else{
                vm.messfolio = 'OK';
                vm.isSaving = false;
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
    }
})();




