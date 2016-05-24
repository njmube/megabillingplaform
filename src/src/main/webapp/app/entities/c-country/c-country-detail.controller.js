(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_countryDetailController', C_countryDetailController);

    C_countryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'C_country', 'C_state'];

    function C_countryDetailController($scope, $rootScope, $stateParams, entity, C_country, C_state) {
        var vm = this;
        vm.c_country = entity;
        vm.load = function (id) {
            C_country.get({id: id}, function(result) {
                vm.c_country = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:c_countryUpdate', function(event, result) {
            vm.c_country = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
