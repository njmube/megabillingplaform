(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_conceptDetailController', Free_conceptDetailController);

    Free_conceptDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Free_concept', 'Free_cfdi', 'Measure_unit'];

    function Free_conceptDetailController($scope, $rootScope, $stateParams, entity, Free_concept, Free_cfdi, Measure_unit) {
        var vm = this;
        vm.free_concept = entity;
        vm.load = function (id) {
            Free_concept.get({id: id}, function(result) {
                vm.free_concept = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:free_conceptUpdate', function(event, result) {
            vm.free_concept = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
