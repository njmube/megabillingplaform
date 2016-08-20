(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_ecc11_conceptDialogController', Freecom_ecc11_conceptDialogController);

    Freecom_ecc11_conceptDialogController.$inject = ['$uibModalInstance', 'entity', 'Freecom_product_key', 'C_tar', '$uibModal'];

    function Freecom_ecc11_conceptDialogController ($uibModalInstance, entity, Freecom_product_key, C_tar, $uibModal) {
        var vm = this;

        vm.freecom_ecc11_concept = entity;
        vm.clear = clear;
        vm.save = save;
        vm.freecom_product_keys = Freecom_product_key.query();
        vm.c_tars = C_tar.query();

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.date = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            $uibModalInstance.close({
                concept: vm.freecom_ecc11_concept,
                transfers: vm.freecom_ecc11_transfers
            });
            vm.isSaving = false;
        }

        function floorFigure(figure, decimals){
            if (!decimals) decimals = 2;
            var d = Math.pow(10,decimals);
            return (parseInt(figure*d)/d).toFixed(decimals);
        }

        vm.onUnitValueQuantityChange = function(){
            if(vm.freecom_ecc11_concept.quantity != null && vm.freecom_ecc11_concept.unit_value != null){
                var amount = vm.freecom_ecc11_concept.quantity * vm.freecom_ecc11_concept.unit_value;
                vm.freecom_ecc11_concept.amount = floorFigure(amount, 2);
            }
        };

        vm.freecom_ecc11_transfers = [];

        vm.addTransfer = function(){
            $uibModal.open({
                templateUrl: 'app/entities/freecom-ecc-11-transfer/freecom-ecc-11-transfer-dialog.html',
                controller: 'Freecom_ecc11_transferDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: '',
                resolve: {
                    entity: function () {
                        return {
                            type_tax: null,
                            rate: null,
                            amount: null,
                            id: null
                        };
                    }
                }
            }).result.then(function(result) {
                vm.freecom_ecc11_transfers.push(result);
            }, function() {
                //do not nothing
            });
        };

        vm.removeTransfer = function(index){
            vm.freecom_ecc11_transfers.splice(index,1);
        };
    }
})();
