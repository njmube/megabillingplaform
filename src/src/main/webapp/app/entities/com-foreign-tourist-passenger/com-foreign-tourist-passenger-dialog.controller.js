(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_foreign_tourist_passengerDialogController', Com_foreign_tourist_passengerDialogController);

    Com_foreign_tourist_passengerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Com_foreign_tourist_passenger', 'C_transit_type', 'C_type_road', 'Cfdi'];

    function Com_foreign_tourist_passengerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Com_foreign_tourist_passenger, C_transit_type, C_type_road, Cfdi) {
        var vm = this;
        vm.com_foreign_tourist_passenger = entity;
        vm.c_transit_types = C_transit_type.query();
        vm.c_type_roads = C_type_road.query();
        vm.cfdis = Cfdi.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:com_foreign_tourist_passengerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.com_foreign_tourist_passenger.id !== null) {
                Com_foreign_tourist_passenger.update(vm.com_foreign_tourist_passenger, onSaveSuccess, onSaveError);
            } else {
                Com_foreign_tourist_passenger.save(vm.com_foreign_tourist_passenger, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.date_traffic = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
