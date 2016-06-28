(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_receiverDeleteController',Free_receiverDeleteController);

    Free_receiverDeleteController.$inject = ['$uibModalInstance', 'entity', 'Free_receiver'];

    function Free_receiverDeleteController($uibModalInstance, entity, Free_receiver) {
        var vm = this;
        vm.free_receiver = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Free_receiver.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
