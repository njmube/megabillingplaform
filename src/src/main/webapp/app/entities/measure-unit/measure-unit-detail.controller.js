(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Measure_unitDetailController', Measure_unitDetailController);

    Measure_unitDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Measure_unit'];

    function Measure_unitDetailController($scope, $rootScope, $stateParams, entity, Measure_unit) {
        var vm = this;
        vm.measure_unit = entity;
        vm.load = function (id) {
            Measure_unit.get({id: id}, function(result) {
                vm.measure_unit = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:measure_unitUpdate', function(event, result) {
            vm.measure_unit = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
