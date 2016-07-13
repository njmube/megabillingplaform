(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('School_levelDialogController', School_levelDialogController);

    School_levelDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'School_level'];

    function School_levelDialogController ($scope, $stateParams, $uibModalInstance, entity, School_level) {
        var vm = this;
        vm.school_level = entity;
        vm.load = function(id) {
            School_level.get({id : id}, function(result) {
                vm.school_level = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:school_levelUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.school_level.id !== null) {
                School_level.update(vm.school_level, onSaveSuccess, onSaveError);
            } else {
                School_level.save(vm.school_level, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
