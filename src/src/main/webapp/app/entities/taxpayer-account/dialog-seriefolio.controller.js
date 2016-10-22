(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('DialogSerieFolioController', DialogSerieFolioController);

    DialogSerieFolioController.$inject = ['$scope', '$filter','$stateParams', '$uibModal', 'Taxpayer_series_folio', 'Taxpayer_account', '$uibModalInstance', '$q', 'entity', 'DataUtils', 'Tax_regime', 'C_country', 'C_state', 'C_municipality', 'C_colony', 'C_zip_code','Type_taxpayer'];

    function DialogSerieFolioController ($scope, $filter, $stateParams, $uibModal, Taxpayer_series_folio, Taxpayer_account, $uibModalInstance, $q, entity, DataUtils, Tax_regime, C_country, C_state, C_municipality, C_colony, C_zip_code, Type_taxpayer) {
        var vm = this;

        vm.taxpayer_series_folio = entity;
        vm.showInfo = false;
        vm.add = add;
        vm.messfolio = null;

        function add(){
            vm.isSaving = true;
            vm.messfolio = null;
            if(vm.taxpayer_series_folio.folio_start <= vm.taxpayer_series_folio.folio_current &&
                vm.taxpayer_series_folio.folio_end >= vm.taxpayer_series_folio.folio_current){
                Taxpayer_series_folio.update(vm.taxpayer_series_folio, onSaveSuccess, onSaveError);
            }
            else{
                vm.messfolio = 'OK';
                vm.isSaving = false;
            }
        }

        var onSaveSuccess = function (result) {
            vm.showInfo = false;
            $uibModalInstance.close({
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





