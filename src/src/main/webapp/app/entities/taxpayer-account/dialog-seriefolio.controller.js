(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('DialogSerieFolioController', DialogSerieFolioController);

    DialogSerieFolioController.$inject = ['$scope', '$filter','AlertService', '$stateParams', '$uibModal', 'Taxpayer_series_folio', 'Taxpayer_account', '$uibModalInstance', '$q', 'entity', 'DataUtils', 'Tax_regime', 'C_country', 'C_state', 'C_municipality', 'C_colony', 'C_zip_code','Type_taxpayer'];

    function DialogSerieFolioController ($scope, $filter, AlertService, $stateParams, $uibModal, Taxpayer_series_folio, Taxpayer_account, $uibModalInstance, $q, entity, DataUtils, Tax_regime, C_country, C_state, C_municipality, C_colony, C_zip_code, Type_taxpayer) {
        var vm = this;

        vm.taxpayer_series_folio = entity;
        vm.showInfo = false;
        vm.add = add;
        vm.messfolio = null;
        vm.messenable = null;
        vm.clicEdit = clicEdit;

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

        function clicEdit(){
            if(vm.taxpayer_series_folio.enable == true){
                vm.taxpayer_series_folios = Taxpayer_series_folio.query({
                    taxpayeraccount: vm.taxpayer_series_folio.taxpayer_account.id
                },onSuccess, onError);
            }
        }

        function onSuccess(data) {
            vm.taxpayer_series_folios = data;

            for(var i = 0; i < vm.taxpayer_series_folios.length;i++){
                if(vm.taxpayer_series_folios[i].enable && vm.taxpayer_series_folio.id != vm.taxpayer_series_folios[i].id){
                    vm.messenable = 'OK';
                    vm.taxpayer_series_folio.enable = false;
                }
            }
        }
        function onError(error) {
            AlertService.error(error.data.message);
        }


        vm.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();





