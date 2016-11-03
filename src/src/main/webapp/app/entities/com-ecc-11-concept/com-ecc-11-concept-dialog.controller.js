(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_ecc_11_conceptDialogController', Com_ecc_11_conceptDialogController);

    Com_ecc_11_conceptDialogController.$inject = ['$uibModalInstance', 'entity', 'Com_product_key', 'C_tar', '$uibModal'];

    function Com_ecc_11_conceptDialogController ($uibModalInstance, entity, Com_product_key, C_tar, $uibModal) {
        var vm = this;

        vm.com_ecc_11_concept = entity;
        vm.clear = clear;
        vm.save = save;
        vm.com_product_keys = Com_product_key.query();
        vm.c_tars = C_tar.query();

        vm.minDate = new Date('2015-01-01T00:00:00Z');
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
                concept: vm.com_ecc_11_concept,
                transfers: vm.com_ecc_11_transfers
            });
            vm.isSaving = false;
        }

        function floorFigure(figure, decimals){
            if (!decimals) decimals = 2;
            var d = Math.pow(10,decimals);
            return (parseInt(figure*d)/d).toFixed(decimals);
        }

        vm.onUnitValueQuantityChange = function(){
            if(vm.com_ecc_11_concept.quantity != null && vm.com_ecc_11_concept.unit_value != null){
                var amount = vm.com_ecc_11_concept.quantity * vm.com_ecc_11_concept.unit_value;
                vm.com_ecc_11_concept.amount = floorFigure(amount, 2);
            }
        };

        vm.com_ecc_11_transfers = [];

        vm.addTransfer = function(){
            $uibModal.open({
                templateUrl: 'app/entities/com-ecc-11-transfer/com-ecc-11-transfer-dialog.html',
                controller: 'Com_ecc_11_transferDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: '',
                resolve: {
                    entity: function () {
                        return {
                            type_tax: null,
                            rate: "0.00",
                            amount: "0.01",
                            id: null
                        };
                    }
                }
            }).result.then(function(result) {
                vm.com_ecc_11_transfers.push(result);
            });
        };

        vm.removeTransfer = function(index){
            vm.com_ecc_11_transfers.splice(index,1);
        };
    }
})();
