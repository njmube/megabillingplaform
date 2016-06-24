(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_part_conceptDeleteController',Free_part_conceptDeleteController);

    Free_part_conceptDeleteController.$inject = ['$uibModalInstance', 'entity', 'Free_part_concept'];

    function Free_part_conceptDeleteController($uibModalInstance, entity, Free_part_concept) {
        var vm = this;
        vm.free_part_concept = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Free_part_concept.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
