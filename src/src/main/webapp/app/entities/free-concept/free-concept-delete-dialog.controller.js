(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_conceptDeleteController',Free_conceptDeleteController);

    Free_conceptDeleteController.$inject = ['$uibModalInstance', 'entity', 'Free_concept'];

    function Free_conceptDeleteController($uibModalInstance, entity, Free_concept) {
        var vm = this;
        vm.free_concept = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Free_concept.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
