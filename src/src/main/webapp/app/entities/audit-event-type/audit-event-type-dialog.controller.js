(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Audit_event_typeDialogController', Audit_event_typeDialogController);

    Audit_event_typeDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Audit_event_type'];

    function Audit_event_typeDialogController ($scope, $stateParams, $uibModalInstance, entity, Audit_event_type) {
        var vm = this;
        vm.audit_event_type = entity;
        vm.load = function(id) {
            Audit_event_type.get({id : id}, function(result) {
                vm.audit_event_type = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:audit_event_typeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.audit_event_type.id !== null) {
                Audit_event_type.update(vm.audit_event_type, onSaveSuccess, onSaveError);
            } else {
                Audit_event_type.save(vm.audit_event_type, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
