(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_reciverDeleteController',Free_reciverDeleteController);

    Free_reciverDeleteController.$inject = ['$uibModalInstance', 'entity', 'Free_reciver'];

    function Free_reciverDeleteController($uibModalInstance, entity, Free_reciver) {
        var vm = this;
        vm.free_reciver = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Free_reciver.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
