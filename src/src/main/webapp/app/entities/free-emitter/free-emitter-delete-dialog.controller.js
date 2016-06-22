(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_emitterDeleteController',Free_emitterDeleteController);

    Free_emitterDeleteController.$inject = ['$uibModalInstance', 'entity', 'Free_emitter'];

    function Free_emitterDeleteController($uibModalInstance, entity, Free_emitter) {
        var vm = this;

        vm.free_emitter = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Free_emitter.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
