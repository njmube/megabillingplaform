(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('TracemgDialogController', TracemgDialogController);

    TracemgDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Tracemg', 'Audit_event_type', 'C_state_event'];

    function TracemgDialogController ($scope, $stateParams, $uibModalInstance, entity, Tracemg, Audit_event_type, C_state_event) {
        var vm = this;
        vm.tracemg = entity;
        vm.audit_event_types = Audit_event_type.query();
        vm.c_state_events = C_state_event.query();
        vm.load = function(id) {
            Tracemg.get({id : id}, function(result) {
                vm.tracemg = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:tracemgUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.tracemg.id !== null) {
                Tracemg.update(vm.tracemg, onSaveSuccess, onSaveError);
            } else {
                Tracemg.save(vm.tracemg, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.timestamp = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
