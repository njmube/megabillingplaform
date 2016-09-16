(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_tfdDeleteController',Com_tfdDeleteController);

    Com_tfdDeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_tfd'];

    function Com_tfdDeleteController($uibModalInstance, entity, Com_tfd) {
        var vm = this;

        vm.com_tfd = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Com_tfd.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
