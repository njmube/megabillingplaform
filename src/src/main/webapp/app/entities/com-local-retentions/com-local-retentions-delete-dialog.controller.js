(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_local_retentionsDeleteController',Com_local_retentionsDeleteController);

    Com_local_retentionsDeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_local_retentions'];

    function Com_local_retentionsDeleteController($uibModalInstance, entity, Com_local_retentions) {
        var vm = this;
        vm.com_local_retentions = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Com_local_retentions.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
