(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_concept_fuelDialogController', Com_concept_fuelDialogController);

    Com_concept_fuelDialogController.$inject = ['$uibModalInstance', 'entity','$uibModal'];

    function Com_concept_fuelDialogController ($uibModalInstance, entity, $uibModal) {
        var vm = this;
        vm.com_concept_fuel = entity;

        function floorFigure(figure, decimals){
            if (!decimals) decimals = 2;
            var d = Math.pow(10,decimals);
            return (parseInt(figure*d)/d).toFixed(decimals);
        }

        vm.onNumericValChange = function(){
            if(vm.com_concept_fuel.quantity != null && vm.com_concept_fuel.unit_value != null){
                var amount = vm.com_concept_fuel.quantity * vm.com_concept_fuel.unit_value;
                vm.com_concept_fuel.amount = floorFigure(amount, 2);
            }
        };

        vm.determinates = [];

        vm.addDeterminate = function(){
            $uibModal.open({
                templateUrl: 'app/entities/com-determined/com-determined-dialog.html',
                controller: 'Com_determinedDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: '',
                resolve: {
                    entity: function () {
                        return {
                            rate: null,
                            amount: (0).toFixed(2),
                            id: null
                        };
                    }
                }
            }).result.then(function(result) {
                vm.determinates.push(result);
            });
        };

        vm.removeDeterminate = function(index){
            vm.determinates.splice(index,1);
        };

        vm.save = function () {
            vm.isSaving = true;

            $uibModalInstance.close({
                concept_fuel: vm.com_concept_fuel,
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
