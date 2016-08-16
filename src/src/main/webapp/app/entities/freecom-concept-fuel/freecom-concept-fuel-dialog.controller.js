(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_concept_fuelDialogController', Freecom_concept_fuelDialogController);

    Freecom_concept_fuelDialogController.$inject = ['$uibModalInstance', 'entity','$uibModal'];

    function Freecom_concept_fuelDialogController ($uibModalInstance, entity, $uibModal) {
        var vm = this;
        vm.freecom_concept_fuel = entity;

        function floorFigure(figure, decimals){
            if (!decimals) decimals = 2;
            var d = Math.pow(10,decimals);
            return (parseInt(figure*d)/d).toFixed(decimals);
        }

        vm.onNumericValChange = function(){
            if(vm.freecom_concept_fuel.quantity != null && vm.freecom_concept_fuel.unit_value != null){
                var amount = vm.freecom_concept_fuel.quantity * vm.freecom_concept_fuel.unit_value;
                vm.freecom_concept_fuel.amount = floorFigure(amount, 2);
            }
        };

        vm.determinates = [];

        vm.addDeterminate = function(){
            $uibModal.open({
                templateUrl: 'app/entities/freecom-determined/freecom-determined-dialog.html',
                controller: 'Freecom_determinedDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: '',
                resolve: {
                    entity: function () {
                        return {
                            rate: null,
                            amount: null,
                            id: null
                        };
                    }
                }
            }).result.then(function(result) {
                vm.determinates.push(result);
            }, function() {
                //do not nothing
            });
        };

        vm.removeDeterminate = function(index){
            vm.determinates.splice(index,1);
        };

        vm.save = function () {
            vm.isSaving = true;

            $uibModalInstance.close({
                concept_fuel: {
                    identifier: vm.freecom_concept_fuel.identifier,
                    date_expedition: vm.freecom_concept_fuel.date_expedition,
                    rfc: vm.freecom_concept_fuel.rfc,
                    key_station: vm.freecom_concept_fuel.key_station,
                    quantity: vm.freecom_concept_fuel.quantity,
                    fuel_name: vm.freecom_concept_fuel.fuel_name,
                    folio_operation: vm.freecom_concept_fuel.folio_operation,
                    unit_value: vm.freecom_concept_fuel.unit_value,
                    amount: vm.freecom_concept_fuel.amount,
                    id: null
                },
                determinates: vm.determinates
            });

            vm.isSaving = false;
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.date_expedition = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
