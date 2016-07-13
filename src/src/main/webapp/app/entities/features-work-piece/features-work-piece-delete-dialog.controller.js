(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Features_work_pieceDeleteController',Features_work_pieceDeleteController);

    Features_work_pieceDeleteController.$inject = ['$uibModalInstance', 'entity', 'Features_work_piece'];

    function Features_work_pieceDeleteController($uibModalInstance, entity, Features_work_piece) {
        var vm = this;
        vm.features_work_piece = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Features_work_piece.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
