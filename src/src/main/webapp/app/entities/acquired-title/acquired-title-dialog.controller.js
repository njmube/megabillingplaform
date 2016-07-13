(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Acquired_titleDialogController', Acquired_titleDialogController);

    Acquired_titleDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Acquired_title'];

    function Acquired_titleDialogController ($scope, $stateParams, $uibModalInstance, entity, Acquired_title) {
        var vm = this;
        vm.acquired_title = entity;
        vm.load = function(id) {
            Acquired_title.get({id : id}, function(result) {
                vm.acquired_title = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:acquired_titleUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.acquired_title.id !== null) {
                Acquired_title.update(vm.acquired_title, onSaveSuccess, onSaveError);
            } else {
                Acquired_title.save(vm.acquired_title, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
