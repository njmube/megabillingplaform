(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('CommitteeDialogController', CommitteeDialogController);

    CommitteeDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Committee'];

    function CommitteeDialogController ($scope, $stateParams, $uibModalInstance, entity, Committee) {
        var vm = this;
        vm.committee = entity;
        vm.load = function(id) {
            Committee.get({id : id}, function(result) {
                vm.committee = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:committeeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.committee.id !== null) {
                Committee.update(vm.committee, onSaveSuccess, onSaveError);
            } else {
                Committee.save(vm.committee, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
