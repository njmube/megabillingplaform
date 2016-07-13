(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Features_work_pieceDialogController', Features_work_pieceDialogController);

    Features_work_pieceDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Features_work_piece'];

    function Features_work_pieceDialogController ($scope, $stateParams, $uibModalInstance, entity, Features_work_piece) {
        var vm = this;
        vm.features_work_piece = entity;
        vm.load = function(id) {
            Features_work_piece.get({id : id}, function(result) {
                vm.features_work_piece = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:features_work_pieceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.features_work_piece.id !== null) {
                Features_work_piece.update(vm.features_work_piece, onSaveSuccess, onSaveError);
            } else {
                Features_work_piece.save(vm.features_work_piece, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
