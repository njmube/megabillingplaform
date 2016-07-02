(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_municipalityDetailController', C_municipalityDetailController);

    C_municipalityDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'C_municipality', 'C_state'];

    function C_municipalityDetailController($scope, $rootScope, $stateParams, entity, C_municipality, C_state) {
        var vm = this;
        vm.c_municipality = entity;
        vm.load = function (id) {
            C_municipality.get({id: id}, function(result) {
                vm.c_municipality = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:c_municipalityUpdate', function(event, result) {
            vm.c_municipality = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
