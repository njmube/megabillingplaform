(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_foreign_tourist_passengerDialogController', Freecom_foreign_tourist_passengerDialogController);

    Freecom_foreign_tourist_passengerDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Freecom_foreign_tourist_passenger', 'C_transit_type', 'C_type_road', 'Free_cfdi'];

    function Freecom_foreign_tourist_passengerDialogController ($scope, $stateParams, $uibModalInstance, entity, Freecom_foreign_tourist_passenger, C_transit_type, C_type_road, Free_cfdi) {
        var vm = this;
        vm.freecom_foreign_tourist_passenger = entity;
        vm.c_transit_types = C_transit_type.query();
        vm.c_type_roads = C_type_road.query();
        vm.free_cfdis = Free_cfdi.query();
        vm.load = function(id) {
            Freecom_foreign_tourist_passenger.get({id : id}, function(result) {
                vm.freecom_foreign_tourist_passenger = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:freecom_foreign_tourist_passengerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.freecom_foreign_tourist_passenger.id !== null) {
                Freecom_foreign_tourist_passenger.update(vm.freecom_foreign_tourist_passenger, onSaveSuccess, onSaveError);
            } else {
                Freecom_foreign_tourist_passenger.save(vm.freecom_foreign_tourist_passenger, onSaveSuccess, onSaveError);
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
