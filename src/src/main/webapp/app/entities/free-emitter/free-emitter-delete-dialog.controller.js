(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_emitterDeleteController',Free_emitterDeleteController);

    Free_emitterDeleteController.$inject = ['$uibModalInstance', 'entity', 'Free_emitter'];

    function Free_emitterDeleteController($uibModalInstance, entity, Free_emitter) {
        var vm = this;
        vm.free_emitter = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Free_emitter.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
