(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_features_work_pieceDialogController', C_features_work_pieceDialogController);

    C_features_work_pieceDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'C_features_work_piece'];

    function C_features_work_pieceDialogController ($scope, $stateParams, $uibModalInstance, entity, C_features_work_piece) {
        var vm = this;
        vm.c_features_work_piece = entity;
        vm.load = function(id) {
            C_features_work_piece.get({id : id}, function(result) {
                vm.c_features_work_piece = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:c_features_work_pieceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.c_features_work_piece.id !== null) {
                C_features_work_piece.update(vm.c_features_work_piece, onSaveSuccess, onSaveError);
            } else {
                C_features_work_piece.save(vm.c_features_work_piece, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
