(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_features_work_pieceDeleteController',C_features_work_pieceDeleteController);

    C_features_work_pieceDeleteController.$inject = ['$uibModalInstance', 'entity', 'C_features_work_piece'];

    function C_features_work_pieceDeleteController($uibModalInstance, entity, C_features_work_piece) {
        var vm = this;
        vm.c_features_work_piece = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            C_features_work_piece.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
