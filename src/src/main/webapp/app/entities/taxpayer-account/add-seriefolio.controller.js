(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('AddSerieFolioController', AddSerieFolioController);

    AddSerieFolioController.$inject = ['$scope', '$filter','AlertService', '$stateParams', '$uibModal', 'Taxpayer_series_folio', 'Taxpayer_account', '$uibModalInstance', '$q', 'entity', 'DataUtils', 'Tax_regime', 'C_country', 'C_state', 'C_municipality', 'C_colony', 'C_zip_code','Type_taxpayer'];

    function AddSerieFolioController ($scope, $filter, AlertService, $stateParams, $uibModal, Taxpayer_series_folio, Taxpayer_account, $uibModalInstance, $q, entity, DataUtils, Tax_regime, C_country, C_state, C_municipality, C_colony, C_zip_code, Type_taxpayer) {
        var vm = this;

        vm.taxpayer_account = entity;
        vm.showInfo = false;
        vm.taxpayer_series_folio = {};
        vm.taxpayer_series_folios = [];
        var today = new Date();
        vm.toDate = new Date(today.getFullYear(), today.getMonth(), today.getDate() + 1);
        vm.messfolio = null;
        vm.messenable = null;
        vm.clicEdit = clicEdit;

        vm.add = add;

        vm.load = function(id) {
            Taxpayer_account.get({id : id}, function(result) {
                vm.taxpayer_account = result;
            });
        };

        function add(){
            vm.isSaving = true;
            vm.messfolio = null;
            vm.messenable = null;
            vm.taxpayer_series_folio.taxpayer_account = vm.taxpayer_account;
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

        function clicEdit(){
            if(vm.taxpayer_series_folio.enable == true){
                vm.taxpayer_series_folios = Taxpayer_series_folio.query({
                    taxpayeraccount: vm.taxpayer_account.id
                },onSuccess, onError);
            }
        }

        function onSuccess(data) {
            vm.taxpayer_series_folios = data;

            for(var i = 0; i < vm.taxpayer_series_folios.length;i++){
                if(vm.taxpayer_series_folios[i].enable){
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




