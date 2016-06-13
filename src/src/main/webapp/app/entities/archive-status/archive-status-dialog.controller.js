(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Archive_statusDialogController', Archive_statusDialogController);

    Archive_statusDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Archive_status'];

    function Archive_statusDialogController ($scope, $stateParams, $uibModalInstance, entity, Archive_status) {
        var vm = this;
        vm.archive_status = entity;
        vm.load = function(id) {
            Archive_status.get({id : id}, function(result) {
                vm.archive_status = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:archive_statusUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.archive_status.id !== null) {
                Archive_status.update(vm.archive_status, onSaveSuccess, onSaveError);
            } else {
                Archive_status.save(vm.archive_status, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.date = false;
        vm.datePickerOpenStatus.date1 = false;
        vm.datePickerOpenStatus.date_born = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
