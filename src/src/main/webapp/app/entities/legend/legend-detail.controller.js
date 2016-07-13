(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('LegendDetailController', LegendDetailController);

    LegendDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Legend', 'Freecom_taxlegends'];

    function LegendDetailController($scope, $rootScope, $stateParams, entity, Legend, Freecom_taxlegends) {
        var vm = this;
        vm.legend = entity;
        vm.load = function (id) {
            Legend.get({id: id}, function(result) {
                vm.legend = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:legendUpdate', function(event, result) {
            vm.legend = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
